This Utility is developed by Hitesh Dhanwani for the monitoring of Siebel Servers and components.
This utility is also capable of spooling SQL results relevant to know the Health of Servers. The SQL queries can be modified accordingly.

Configuration Setup:
1. Place the Folder HealthCheck on the server for which healthcheck needs to be done.
2. Change the path in the following files accordingly.
	a. HealthCheck.bat
	b. listcomp.txt
	c. rcr.sql
	d. tablespace.sql
	e. queuedmail.sql
3. The SQL statement can be modified as per our need.
4. If we modify our SQL statements we might require to modify our ReportGen.java accordingly.
5. ReportGen.java is a smart JAVA program which reads the log files and fetches only the relevant content and generates a final consolidated HTML report.
6. After all the configuration is done execute HealthCheck.bat .

For Help in configuration reach me at - dhanwanihitesh9@gmail.com or https://www.facebook.com/hitesh.dhanwani

