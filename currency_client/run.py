from flask import Flask, render_template, request, jsonify
from zeep import Client
import os

URL = 'http://localhost:8080/currency_server/services/ServerImpl?WSDL'

app = Flask(__name__, static_url_path="", static_folder="static")
app.config['SECRET_KEY'] = os.getenv('APP_SEC')


@app.route("/")
def hello_world():
  try:
    client = Client(URL)
  except:
    print('Either WSDL url is incorrect or server is not online.')
  try:
    currency_str = client.service.currencyData()
  except:
    return render_template("error.html", error="An error occured while retreiving currency data.")
  currency_list = currency_str.split(",")
  currency_list.sort()
  context = {
    'currency_list':currency_list
  }
  return render_template("converter.html", context=context)

@app.route("/convert-currency/", methods=['GET','POST'])
def title_quick_search():
  if request.method == 'POST':
    data = request.get_json()
    srcValue  = data['sourceAmount']
    srcCurrency  = data['sourceCurr']
    reqCurrency  = data['requestCurr']
    try:
      client = Client(URL)
      currency_str = client.service.convert(srcValue, srcCurrency, reqCurrency)
      currency_str = round(currency_str,12)
      return jsonify({'value': currency_str,'done': True})
    except:
      return jsonify({'value': 0.0,'done': False, "message": "Failed to make the connection to the server."})
  else:
    return jsonify({"error": True, "status": 403, "message": "Invalid Request Method."})

if __name__ == '__main__':
  app.run(host='127.0.0.1', port=5050, debug=True, threaded=False)