@echo off
echo ============================================================
echo Automated Test Coverage Improvement Workflow
echo ============================================================
cd /d "%~dp0"
python run_full_workflow.py
pause
