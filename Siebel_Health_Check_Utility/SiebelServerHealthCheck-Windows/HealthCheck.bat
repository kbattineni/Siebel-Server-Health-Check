REM	#################################################################################
REM	#	Author		:	Hitesh Dhanwani
REM	#	Description	:	Main bat file to generate Health logs and prepare a reports
REM	#################################################################################

if exist "[Root]\SiebelHealthCheck\server.txt" (
del "[Root]\SiebelHealthCheck\server.txt"
)

if exist "[Root]\SiebelHealthCheck\listserver.txt" (
del "[Root]\SiebelHealthCheck\listserver.txt"
)

if exist "[Root]\SiebelHealthCheck\listcomp.txt" (
del "[Root]\SiebelHealthCheck\listcomp.txt" 
)

if exist "[Root]\SiebelHealthCheck\listtask.txt" (
del "[Root]\SiebelHealthCheck\listtask.txt" 
)

if exist "[Root]\SiebelHealthCheck\queuedmailspool.txt" (
del "[Root]\SiebelHealthCheck\queuedmailspool.txt" 
)

if exist "[Root]\SiebelHealthCheck\rcrspool.txt" (
del "[Root]\SiebelHealthCheck\rcrspool.txt" 
)

if exist "[Root]\SiebelHealthCheck\tablespace.txt" (
del "[Root]\SiebelHealthCheck\tablespace.txt" 
)

if exist "[Root]\SiebelHealthCheck\HealthCheckFinal.html" (
del "[Root]\SiebelHealthCheck\HealthCheckFinal.html" 
)


if exist "[Root]\SiebelHealthCheck\HealthCheckFinal.html" (
del "[Root]\SiebelHealthCheck\Disk.txt" 
)


FOR %%A IN (<List all servers delimeted with comma>) DO (
	@<Path\gtwysrvr\BIN\srvrmgr> /g <Gateway Address> /e <Enterprise Server> /s %%A /u <admin credentials> /p <Password> /i "[Root]\SiebelHealthCheck\listCompTask.txt"  >nul 2>nul
	ECHO %%A >> "[Root]\SiebelHealthCheck\server.txt"
)

@"E:\Apps\oracle\product\11.2.0\BIN\sqlplus" <username>/<Password>@<TNSENTRY> @"[Root]\SiebelHealthCheck\rcr.sql" >nul 2>nul
@"E:\Apps\oracle\product\11.2.0\BIN\sqlplus" <username>/<Password>@<TNSENTRY> @"[Root]\SiebelHealthCheck\queuedmail.sql" >nul 2>nul
@"E:\Apps\oracle\product\11.2.0\BIN\sqlplus" <username>/<Password>@<TNSENTRY> @"[Root]\SiebelHealthCheck\tablespace.sql" >nul 2>nul

cd [Root]\SiebelHealthCheck

"c:\Program Files (x86)\Java\jdk1.6.0_22\bin\javac" ReportGen.java
"c:\Program Files (x86)\Java\jdk1.6.0_22\bin\java" -cp . ReportGen

powershell "[Root]\SiebelHealthCheck\sendmail.ps1"



