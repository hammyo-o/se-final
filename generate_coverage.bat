@echo off
echo ============================================================
echo Generating JaCoCo Coverage Report
echo ============================================================
cd /d "%~dp0\codebase"
echo.
echo Running: mvn clean verify
echo.
mvn clean verify
echo.
echo ============================================================
if exist "target\site\jacoco\jacoco.xml" (
    echo SUCCESS! Coverage report generated at:
    echo %CD%\target\site\jacoco\jacoco.xml
) else (
    echo ERROR: Coverage report was not generated!
)
echo ============================================================
pause
