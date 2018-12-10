SET ServerOutput ON
SET ServerOutput ON
set echo off
set verify off
set termout on
set heading off
set feedback off
set newpage none
set linesize 270
spool rcrspool.txt
Declare
counter  number:=0;
Cursor FetchRCRJob Is 
	   select a.ROW_ID , a.STATUS , b.DISPLAY_NAME 
	   from S_SRM_REQUEST a, S_SRM_ACTION b 
	   where a.REQ_TYPE_CD = 'RPT_PARENT' and a.STATUS = 'ACTIVE' and a.ACTION_ID = b.ROW_ID;
v_FetchRCRJob FetchRCRJob%ROWTYPE;
ROW_IDD S_SRM_REQUEST.ROW_ID%TYPE;
Cursor FetchRCRTask Is 
	   select ROW_ID ,STATUS ,SCHED_START_DT ,ACTL_START_DT  ,ACTL_END_DT 
	   from S_SRM_REQUEST where par_req_id = ROW_IDD and row_id <> ROW_IDD order by SCHED_START_DT desc;
v_FetchRCRTask FetchRCRTask%ROWTYPE;
Begin
	 DBMS_OUTPUT.ENABLE (1000000);
	 For v_FetchRCRJob IN FetchRCRJob LOOP
	 	 DBMS_OUTPUT.PUT_LINE('Job ,'||v_FetchRCRJob.ROW_ID||','|| v_FetchRCRJob.STATUS||','|| v_FetchRCRJob.DISPLAY_NAME);
		 ROW_IDD := v_FetchRCRJob.ROW_ID;
		 counter:=0;
	 	 For v_FetchRCRTask IN FetchRCRTask LOOP
		 	 IF (counter < 3) THEN
	 	 	 	DBMS_OUTPUT.PUT_LINE('Task,'||v_FetchRCRTask.ROW_ID||','|| v_FetchRCRTask.STATUS||','|| TO_CHAR(v_FetchRCRTask.SCHED_START_DT,'YYYY-MM-DD HH:MI:SS AM')||','|| TO_CHAR(v_FetchRCRTask.ACTL_START_DT,'YYYY-MM-DD HH:MI:SS AM') ||','|| TO_CHAR(v_FetchRCRTask.ACTL_END_DT,'YYYY-MM-DD HH:MI:SS AM'));
				counter:=counter+1;
			 END IF;
	 	 End Loop;
	 End Loop;
END;

/
SET ServerOutput OFF	 
spool off;
exit
