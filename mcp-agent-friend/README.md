# MCP Git + Maven Automation Agent
│ README.md
│ requirements.txt
│
└── docs/
│ reflection.tex
│ reflection.pdf
```


---


## Create a Virtual Environment
Using uv:
```
uv venv
. .venv/Scripts/activate (Windows)
```


---


## Install Python Dependencies
```
uv pip install -r requirements.txt
```


---


## Run the MCP Server
Inside the `mcp-agent` folder:
```
python server.py
```
You should see the server start without errors.


---


## Connect MCP Server to VS Code
In VS Code:
1. Press Ctrl+Shift+P
2. Search: MCP: Add Local Server
3. Enter the command you use to run the server


After this, VS Code Chat will recognize your tools.


---


# 3. Troubleshooting


### Problem: `pdflatex` not recognized
**Fix:**
- Install MiKTeX
- Restart terminal
- Try again


### Problem: VS Code says it "cannot reach server"
**Fix:**
- Make sure virtual environment is activated
- Make sure you're inside the `mcp-agent` folder
- Rerun `python server.py`


### Problem: Maven not found
**Fix:**
- Add Maven to PATH
- Restart your terminal


---


# 4. FAQ
**Q: Where should the reflection.tex file go?**
A: Inside `mcp-agent/docs/` so the PDF stays organized.


**Q: Can I run tests on a different project?**
A: Yes, just update the target Maven folder path inside the MCP server script.


**Q: Do I need admin rights for MiKTeX?**
A: No, the basic installer is enough.


---


# 5. Reflection Summary
The full reflection is in `docs/reflection.tex`, but in summary:
- Coverage improved because the agent consistently ran tests after every change.
- I learned a lot about using AI to automate simple development tasks.
- Future work could include more auto-test generation or static analysis.


---


End of README.