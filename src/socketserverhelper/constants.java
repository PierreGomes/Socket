package socketserverhelper;
public class constants {
    private final String RESPONSEHEADEREXAMPLE = 
                                            "Access-Control-Allow-Origin: https://ah.we.imply.com \n" +
                                            "Access-Control-Allow-Headers: Content-Type\n" +
                                            "Access-Control-Allow-Methods: POST\n" +
                                            "Access-Control-Expose-Headers: Content-Type, Cache-Control, Content-Length, Authorization\n" ;
    private static String JSONTest =   "{\n" +
                                "  \"id\": 17507387,\n" +
                                "  \"method\": \"imprimir\",\n" +
                                "  \"params\": [\n" +
                                "    \"0\",\n" +
                                "    {\n" +
                                "      \"template\": \"PHN0YXJ0Lz48bGFyZ3VyYV9wYWdpbmEgbWlsaW1ldHJvcz0iODAiLz48Yz5DT01QUk9WQU5URTxicj48cGFkIHRhbT0iMyIgY2hyPSIwIiBsYWRvPSJMIj4xPC9wYWQ+IHggPHBhZCB0YW09IjQwIiBjaHI9IiAiIGxhZG89IlIiPjAsMTAgUFJPRDwvcGFkPjxwYWQgdGFtPSIxMCIgY2hyPSIgIiBsYWRvPSJMIj5SJCAwLDEwPC9wYWQ+PGJyPjxicj48cGFkIHRhbT0nNDEnIGNocj0nICcgbGFkbz0nTCc+LS0tLS0tLTwvcGFkPjxicj48IS0tIEJFR0lOIHBhZ190b3RhbF9tb2VkYSAtLT48cGFkIHRhbT0nNDAnIGNocj0nICcgbGFkbz0nTCc+QlJMIDAsMTA8L3BhZD48YnI+PCEtLSBFTkQgcGFnX3RvdGFsX21vZWRhIC0tPkZPUk1BUyBERSBQQUdBTUVOVE88YnI+PCEtLSBCRUdJTiBmb3JtYXNwYWcgLS0+PHBhZCB0YW09IjI4IiBjaHI9IiAiIGxhZG89IlIiPkRJTkhFSVJPPC9wYWQ+PHBhZCB0YW09IjEwIiBjaHI9IiAiIGxhZG89IkwiPiAwLDEwPC9wYWQ+PGJyPjwhLS0gRU5EIGZvcm1hc3BhZyAtLT48YnI+PGJyPjxwYWQgdGFtPScxNScgY2hyPScgJyBsYWRvPSdSJz5OUk8gUmVjaWJvPC9wYWQ+OjxwYWQgdGFtPScyNScgY2hyPScgJyBsYWRvPSdMJz4zNDwvcGFkPjxicj48cGFkIHRhbT0nMTUnIGNocj0nICcgbGFkbz0nUic+RW1pc3PDo288L3BhZD46PHBhZCB0YW09JzI1JyBjaHI9JyAnIGxhZG89J0wnPjEwLzA5LzIwMjEgMDk6MDk6MDY8L3BhZD48YnI+PHBhZCB0YW09JzE1JyBjaHI9JyAnIGxhZG89J1InPkZ1bmNpb27DoXJpbzwvcGFkPjo8cGFkIHRhbT0nMjUnIGNocj0nICcgbGFkbz0nTCc+TUFVUklDSU8gSEVMRkVSIEtSRVRaTUFOTjwvcGFkPjxicj48cGFkIHRhbT0nMTUnIGNocj0nICcgbGFkbz0nUic+UERWLU7Dum1lcm88L3BhZD46PHBhZCB0YW09JzI1JyBjaHI9JyAnIGxhZG89J0wnPlBEVl9TSTwvcGFkPjxicj48c21hbGw+Q05QSjogMDAuMDAwLjAwMC8wMDAwLTAwIC0gSS5NLjogMC4wMDAuMDAwLTA8YnI+Vm9sdGUgc2VtcHJlITxicj48YnI+UG93ZXJlZCBieSBpbXBseS5jb208L3NtYWxsPjwvYz48ZmVlZCBuPSIzIiAvPjx0b3RhbF9jdXQvPjxlamVjdCB0PSIwIiAvPg==\",\n" +
                                "      \"usa_driver\": \"S\",\n" +
                                "      \"usa_base64\": \"S\",\n" +
                                "      \"usar_impresora_recibo\": true,\n" +
                                "      \"usar_impresora_ingresso\": false\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}"
    ;
    
    private static final String POSTEXAMPLE =   "POST /Impressora HTTP/1.1\n" +
                            "Host: 127.0.0.1:12222\n" +
                            "Connection: keep-alive\n" +
                            "Content-Length: 1593\n" +
                            "sec-ch-ua: \"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"\n" +
                            "Accept: application/json, text/javascript, */*; q=0.01\n" +
                            "Content-Type: application/json\n" +
                            "sec-ch-ua-mobile: ?0\n" +
                            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36\n" +
                            "sec-ch-ua-platform: \"Windows\"\n" +
                            "Origin: https://ah.we.imply.com\n" +
                            "Sec-Fetch-Site: cross-site\n" +
                            "Sec-Fetch-Mode: cors\n" +
                            "Sec-Fetch-Dest: empty\n" +
                            "Accept-Encoding: gzip, deflate, br\n" +
                            "Accept-Language: pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                            "\n" +
                            "{\"id\":83418462,\"method\":\"imprimir\",\"params\":[\"0\",{\"template\":\"PHN0YXJ0Lz48bGFyZ3VyYV9wYWdpbmEgbWlsaW1ldHJvcz0iODAiLz48Yz5DT01QUk9WQU5URTxicj48cGFkIHRhbT0iMyIgY2hyPSIwIiBsYWRvPSJMIj4xPC9wYWQ+IHggPHBhZCB0YW09IjQwIiBjaHI9IiAiIGxhZG89IlIiPjAsMTAgUFJPRDwvcGFkPjxwYWQgdGFtPSIxMCIgY2hyPSIgIiBsYWRvPSJMIj5SJCAwLDEwPC9wYWQ+PGJyPjxicj48cGFkIHRhbT0nNDEnIGNocj0nICcgbGFkbz0nTCc+LS0tLS0tLTwvcGFkPjxicj48IS0tIEJFR0lOIHBhZ190b3RhbF9tb2VkYSAtLT48cGFkIHRhbT0nNDAnIGNocj0nICcgbGFkbz0nTCc+QlJMIDAsMTA8L3BhZD48YnI+PCEtLSBFTkQgcGFnX3RvdGFsX21vZWRhIC0tPkZPUk1BUyBERSBQQUdBTUVOVE88YnI+PCEtLSBCRUdJTiBmb3JtYXNwYWcgLS0+PHBhZCB0YW09IjI4IiBjaHI9IiAiIGxhZG89IlIiPkRJTkhFSVJPPC9wYWQ+PHBhZCB0YW09IjEwIiBjaHI9IiAiIGxhZG89IkwiPiAwLDEwPC9wYWQ+PGJyPjwhLS0gRU5EIGZvcm1hc3BhZyAtLT48YnI+PGJyPjxwYWQgdGFtPScxNScgY2hyPScgJyBsYWRvPSdSJz5OUk8gUmVjaWJvPC9wYWQ+OjxwYWQgdGFtPScyNScgY2hyPScgJyBsYWRvPSdMJz40NzwvcGFkPjxicj48cGFkIHRhbT0nMTUnIGNocj0nICcgbGFkbz0nUic+RW1pc3PDo288L3BhZD46PHBhZCB0YW09JzI1JyBjaHI9JyAnIGxhZG89J0wnPjE3LzA5LzIwMjEgMDk6MTE6NDY8L3BhZD48YnI+PHBhZCB0YW09JzE1JyBjaHI9JyAnIGxhZG89J1InPkZ1bmNpb27DoXJpbzwvcGFkPjo8cGFkIHRhbT0nMjUnIGNocj0nICcgbGFkbz0nTCc+QU5UT05JTyBQSUVSUkUgR09NRVM8L3BhZD48YnI+PHBhZCB0YW09JzE1JyBjaHI9JyAnIGxhZG89J1InPlBEVi1Ow7ptZXJvPC9wYWQ+OjxwYWQgdGFtPScyNScgY2hyPScgJyBsYWRvPSdMJz5QRFZfU0k8L3BhZD48YnI+PHNtYWxsPkNOUEo6IDAwLjAwMC4wMDAvMDAwMC0wMCAtIEkuTS46IDAuMDAwLjAwMC0wPGJyPlZvbHRlIHNlbXByZSE8YnI+PGJyPlBvd2VyZWQgYnkgaW1wbHkuY29tPC9zbWFsbD48L2M+PGZlZWQgbj0iMyIgLz48dG90YWxfY3V0Lz48ZWplY3QgdD0iMCIgLz4=\",\"usa_driver\":\"S\",\"usa_base64\":\"S\",\"usar_impresora_recibo\":true,\"usar_impresora_ingresso\":false}]}\n"
                            ;
    
    private static final String OPTIONSEXAMPLE =   "OPTIONS /Impressora HTTP/1.1\n" +
                                            "Host: 127.0.0.1:12222\n" +
                                            "Connection: keep-alive\n" +
                                            "Accept: */*\n" +
                                            "Access-Control-Request-Method: POST\n" +
                                            "Access-Control-Request-Headers: content-type\n" +
                                            "Origin: https://ah.we.imply.com\n" +
                                            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36\n" +
                                            "Sec-Fetch-Mode: cors\n" +
                                            "Sec-Fetch-Site: cross-site\n" +
                                            "Sec-Fetch-Dest: empty\n" +
                                            "Accept-Encoding: gzip, deflate, br\n" +
                                            "Accept-Language: pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7";
    
}
