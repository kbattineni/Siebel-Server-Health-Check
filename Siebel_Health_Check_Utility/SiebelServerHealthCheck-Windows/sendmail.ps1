 $htmlFilePath = ""
 $htmlFileName = "HealthCheckFinal.html"
 $listcomp = "listcomp.txt"	
 $listtask = "listtask.txt"
 $listserver = "listserver.txt"
 $queued = "queuedmailspool.txt"
 $rcr = "rcrspool.txt"
 $server = "server.txt"
 $tablespace = "tablespace.txt"
 $temp = $htmlFilePath + "\" + $htmlFileName

 $EmailFrom = "dhanwanihitesh9@gmail.com"
 $EmailTo = "dhanwanihitesh9@gmail.com"
 $EmailSubject = "Siebel Health Check Report"  
 $SMTPServer = "SMTP Server Address"
 $emailattachment = $temp
 $mailmessage = New-Object system.net.mail.mailmessage 
 $mailmessage.from = ($EmailFrom) 
 $mailmessage.To.add($EmailTo)
 $mailmessage.Subject = $EmailSubject
 $mailmessage.Body = Get-Content $temp

 $Attachment = New-Object System.Net.Mail.Attachment($emailattachment, 'text/html')
 $mailmessage.Attachments.Add($temp)
 $mailmessage.Attachments.Add($listtask)
 $mailmessage.Attachments.Add($listserver)
 $mailmessage.Attachments.Add($queued)
 $mailmessage.Attachments.Add($rcr)
 $mailmessage.Attachments.Add($server)
 $mailmessage.Attachments.Add($tablespace)
 $mailmessage.Attachments.Add($listcomp)

 $mailmessage.IsBodyHTML = $True
 $SMTPClient = New-Object Net.Mail.SmtpClient($SmtpServer, 25)  
 #$SMTPClient.Credentials = New-Object System.Net.NetworkCredential("$SMTPAuthUsername", "$SMTPAuthPassword") 
 $SMTPClient.Send($mailmessage)







