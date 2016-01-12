import 'setimmediate'
import PromiseP from 'promise-polyfill';

// A-> $http function is implemented in order to follow the standard Adapter pattern
function $http(url) {
    // A small example of object
    var core = {

        // Method that performs the ajax request
        ajax: function (method, url, args) {

            // Creating a promise
            var promise = new PromiseP(function(resolve, reject) {
                // Instantiates the XMLHttpRequest
                var client = getXHR();
                var params = objToParams(args.params);
                var formData = objToParams(args.formData);
                var data = JSON.stringify(args.json) || formData || '';

                client.withCredentials = true;
                client.open(method, url + (params || ''));

                if (args.headers) {
                    Object.keys(args.headers).forEach(function (key) {
                        client.setRequestHeader(key, args.headers[key]);
                    });
                }

                client.send(data);
                client.onload = function () {
                    if (this.status >= 200 && this.status < 300) {
                                // Performs the function "resolve" when this.status is equal to 2xx
                        console.log('promise success');
                        resolve(args.json ? JSON.parse(this.response):this.response);
                    } else {
                        // Performs the function "reject" when this.status is different than 2xx
                        console.log('promise failure');
                        reject(this);
                    }
                };
                client.onerror = function () {
                    console.log('promise error');
                    reject(this);
                };
            });

            // Return the promise
            return promise;
        }


    };

    function getXHR() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        }
        try {
            return new ActiveXObject('MSXML2.XMLHTTP.6.0');
        } catch (e) {
            try {
                return new ActiveXObject('MSXML2.XMLHTTP.3.0');
            } catch (e) {
                alert('This browser is not AJAX enabled.');
                return null;
            }
        }
    }

    function objToParams(obj) {

        if (obj && typeof obj === 'object') {
            var params;

            params = Object.keys(obj).map(function (key) {
                return encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]);
            }).join('&');

            return params;
        }
        return false;
    }

    // Adapter pattern
    return {
        'get': function (args) {
            return core.ajax('GET', url, args);
        },
        'post': function (args) {
            return core.ajax('POST', url, args);
        },
        'put': function (args) {
            return core.ajax('PUT', url, args);
        },
        'delete': function (args) {
            return core.ajax('DELETE', url, args);
        }
    };
}

module.exports = $http;