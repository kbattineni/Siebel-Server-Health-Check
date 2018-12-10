SET ServerOutput ON
SET ServerOutput ON
set echo off
set verify off
set termout on
set heading off
set feedback off
set newpage none
set linesize 270
spool queuedmailspool.txt
select e.ROW_ID ||','|| e.EVT_STAT_CD||','|| e.CREATED||','||e.NAME||','||em.EMAIL_TO_LINE||','||em.EMAIL_SNDR_ADDR 
from siebel.S_EVT_ACT e, siebel.S_EVT_MAIL em 
where e.EVT_STAT_CD='Queued' and e.CAL_TYPE_CD = 'Communication and Activities' and em.row_id = e.row_id
order by e.CREATED desc 

/
SET ServerOutput OFF	 
spool off;
exit
