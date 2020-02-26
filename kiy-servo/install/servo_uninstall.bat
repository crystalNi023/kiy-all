@ECHO OFF
CLS
ECHO Start uninstall service ...

SET DIR=%CD%
SET PR_UNINSTALL=%DIR%\kiy-servicew.exe //DS//kiy-servo

%PR_UNINSTALL%

ECHO Service uninstall completed
PAUSE