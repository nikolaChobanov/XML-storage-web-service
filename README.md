# XML-storage-web-service
Server side application which exposes web service endpoints for storing and retrieving data entries.

Storage endpoint is accessible by at most 4 clients at a given time. If there are more concurent clients an http status code 429 too many requests is sent back to the client and the request is declined.

The entries are retrieved with pagination and start from page 1.

Sensitive data is hidden from users without permission through the use of JSON views.

Client side applicaiton that validates an xml file against a schema 

SAX is used to parse the document so it doesn't load the whole file in the memory

Each entry is sent to the server through http request.

