import argparse
import xml.etree.ElementTree as ET
from pathlib import Path
from datetime import datetime, timezone

ROOT = Path(__file__).resolve().parent.parent
DEFAULT_HISTORY = ROOT / "docs" / "coverage_history.md"


def aggregate(report_path: Path):
    if not report_path.exists():
        return None
    tree = ET.parse(str(report_path))
    root = tree.getroot()
    instr_missed = instr_covered = branch_missed = branch_covered = 0
    for counter in root.findall('counter'):
        ctype = counter.get('type')
        missed = int(counter.get('missed', '0'))
        covered = int(counter.get('covered', '0'))
        if ctype == 'INSTRUCTION':
            instr_missed += missed
            instr_covered += covered
        elif ctype == 'BRANCH':
            branch_missed += missed
            branch_covered += covered
    instr_total = instr_missed + instr_covered
    branch_total = branch_missed + branch_covered
    instr_pct = (instr_covered / instr_total * 100) if instr_total else 0.0
    branch_pct = (branch_covered / branch_total * 100) if branch_total else 0.0
    return {
        "instruction": {"covered": instr_covered, "total": instr_total, "percent": instr_pct},
        "branch": {"covered": branch_covered, "total": branch_total, "percent": branch_pct},
    }


def append_history(summary, note: str, history_path: Path):
    ts = datetime.now(timezone.utc).isoformat(timespec='seconds')
    line = (
        f"| {ts} | {summary['instruction']['covered']}/{summary['instruction']['total']}"
        f" ({summary['instruction']['percent']:.2f}%) | {summary['branch']['covered']}/{summary['branch']['total']}"
        f" ({summary['branch']['percent']:.2f}%) | {note} |\n"
    )
    with history_path.open("a", encoding="utf-8") as f:
        f.write(line)


def main():
    parser = argparse.ArgumentParser(description="Append coverage summary from a specific JaCoCo report")
    parser.add_argument("--report", required=True, help="Path to jacoco.xml")
    parser.add_argument("--note", default="automated entry", help="Notes column content")
    parser.add_argument("--history", default=str(DEFAULT_HISTORY), help="coverage_history.md path")
    args = parser.parse_args()

    report_path = Path(args.report)
    history_path = Path(args.history)
    summary = aggregate(report_path)
    if summary is None:
        print(f"Coverage report not found: {report_path}")
        return 1
    append_history(summary, args.note, history_path)
    print("Coverage summary:")
    print(summary)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
