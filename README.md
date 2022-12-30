<b>Reg. No.: AA1893</b>

# Currency Converter Server-Client (Web Service - SOAP/WSDL)

The Currency Converter Web service is made using java and it doesn't need database sevices as it reads the json file using a URL. The client application is created using flask framework as a web application.

<br>

## Setup Server

Server is created using java and the server reads currency data using a url there is no need for placing a .json file or a creating database. There are 2 ways to run the server. 

### <b>Using IntelliJ IDEA IDE</b>

1. Import `currency_server\project_files` folder as a project.
1. Add [`json-simple-1.1.1.jar`](https://code.google.com/archive/p/json-simple/downloads) as a dependency of the project structure > modules.
1. Run Server.java file and server will be hosted on `http://localhost:8888/currency_server`

### <b>Using a Tomcat server</b>

1. Get the `currency_server.war` from `currency_server` folder.
1. Install `Apache Tomcat server`(7.0.47).
1. Add `json-simple-1.1.1.jar` file to the `lib\` folder of the Tomcat server.
1. Deploy the `war` file using an `Apache Tomcat server`. (Tested on Apache Tomcat 7.0.47)
1. You can visit WSDL of the site by using `http://localhost:8080/currency_server/services/ServerImpl?wsdl`. (URL could change depending on the configuration settings) 


Full WSDL output looks like this,
!['wsdl page'](https://images2.imgbox.com/55/69/eWQMIzPi_o.png)
High Quality Image: https://images2.imgbox.com/55/69/eWQMIzPi_o.png

<br>
<br>

## Setup Client Web Server

Client Application is made with flask framework.

1. Create a virtual environment and install the required modules using the requirements.txt file.

```
$ python -m venv venv
$ .\venv\Scripts\Activate.ps1
$ pip install -r .\requirements.txt
```

2. Copy all the client directories and files to your project directory.

3. Update the `URL` global variable to your WSDL url. (URL may change depending on the method you are using to host the server.)

4. After that run `run.py` file using `python3 run.py`.(Make sure you are still inside the virtual environment.)

<br>
<br>

## How the application works

There are 2 web services in the web server.


```
1. convert(amountInSourceCurrency: xsd:float, sourceCurrency: xsd:string, targetCurrency: xsd:string) -> convertReturn: xsd:float
2. currencyData() -> currencyDataReturn: xsd:string
```


First function is `convert()` which takes `amountInSourceCurrency`, `sourceCurrency` and, `targetCurrency` and returns the value of the target currency as a float value.
Second Function does not take any extra parameters and it returns a string of comma-separated currency names.

Web Application looks like this,
!['web application'](https://i.imgur.com/xCq6ull.png)
High Quality Image: https://i.imgur.com/xCq6ull.png

Github : https://github.com/re4nightwing/SOAP_WebClient_JavaServer

<center><h4>--- End of the Document ---</h4></center>
