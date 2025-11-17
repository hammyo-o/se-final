import csv

with open('codebase/target/site/jacoco/jacoco.csv', 'r') as f:
    data = list(csv.reader(f))

rows = data[1:]
inst_m = sum(int(r[3]) for r in rows if len(r) > 6)
inst_c = sum(int(r[4]) for r in rows if len(r) > 6)
br_m = sum(int(r[5]) for r in rows if len(r) > 6)
br_c = sum(int(r[6]) for r in rows if len(r) > 6)

inst_tot = inst_m + inst_c
br_tot = br_m + br_c

print(f"Instruction: {inst_c}/{inst_tot} ({inst_c/inst_tot*100:.2f}%)")
print(f"Branch: {br_c}/{br_tot} ({br_c/br_tot*100:.2f}%)")
