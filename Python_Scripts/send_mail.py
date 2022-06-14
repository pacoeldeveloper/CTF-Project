# encoding: UTF-8 
# Authors: Alek Howland, Victor Curiel, Francisco Arenas 
# CTF Advanced Cybersecurity Project

# NOTE: The code base was based on the implementation proposed
# in the following URL: https://realpython.com/python-send-email/

from email.mime.multipart import MIMEMultipart
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email import encoders

import smtplib, ssl

# Mail sender params
body = "Welcome!\nYou have been selected to participate in the cicada 3301 challenge.\n In this message you will find a file. Use it. We want order\n Good luck!\natte -> master"
print("Welcome to the mail sender service. You have already configured a mail address")
password = input("To continue, please type your password and press enter: ")
subject = "Congrats. Your abilities will be tested soon."
sender_email = "santiago.arrubal@gmail.com"

# Receiver params
receiver_email = input("To continue, please type the receiver mail address:")

# Create a multipart message and set headers
message = MIMEMultipart()
message["From"] = sender_email
message["To"] = receiver_email
message["Subject"] = subject

# Add body to email
message.attach(MIMEText(body, "plain"))

filename = "welcome_subjet_0001000.pdf"  # In same directory as script

# Open PDF file in binary mode
with open(filename, "rb") as attachment:
    # Add file as application/octet-stream
    # Email client can usually download this automatically as attachment
    part = MIMEBase("application", "octet-stream")
    part.set_payload(attachment.read())

# Encode file in ASCII characters to send by email    
encoders.encode_base64(part)

# Add header as key/value pair to attachment part
part.add_header(
    "Content-Disposition",
    f"attachment; filename= {filename}",
)

# Add attachment to message and convert message to string
message.attach(part)
text = message.as_string()

# Log in to server using secure context and send email
context = ssl.create_default_context()
with smtplib.SMTP_SSL("smtp.gmail.com", 465, context=context) as server:
    server.login(sender_email, password)
    server.sendmail(sender_email, receiver_email, text)