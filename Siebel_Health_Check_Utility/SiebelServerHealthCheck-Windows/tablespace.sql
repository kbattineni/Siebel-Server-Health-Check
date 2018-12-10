SET ServerOutput ON
SET ServerOutput ON
set echo off
set verify off
set termout on
set heading off
set feedback off
set newpage none
set linesize 270
spool tablespace.txt
SELECT df.tablespace_name ||','||df.total_space_mb ||','||
(df.total_space_mb - fs.free_space_mb) ||','||
fs.free_space_mb ||','||
ROUND(100 * (fs.free_space / df.total_space),2) 
FROM (SELECT tablespace_name, SUM(bytes) TOTAL_SPACE,
      ROUND(SUM(bytes) / 1048576) TOTAL_SPACE_MB
      FROM dba_data_files
      GROUP BY tablespace_name) df,
     (SELECT tablespace_name, SUM(bytes) FREE_SPACE,
       ROUND(SUM(bytes) / 1048576) FREE_SPACE_MB
       FROM dba_free_space
       GROUP BY tablespace_name) fs
WHERE df.tablespace_name like'CG%' and df.tablespace_name = fs.tablespace_name(+)
ORDER BY fs.tablespace_name;

/
SET ServerOutput OFF	 
spool off;
exit