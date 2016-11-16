// var Debug = require('console-debug')

// var console = new Debug({
// uncaughtExceptionCatch: false,                   // Do we want to catch uncaughtExceptions?
// consoleFilter:          [],         // Filter these console output types
// logToFile:              true,                    // if true, will put console output in a log file folder called 'logs'
// logFilter:              ['LOG','DEBUG','INFO'],  // Examples: Filter these types to not log to file
// colors:                 true                     // do we want pretty pony colors in our console output?
// })

// console.log('I am a log!')
// console.warn('I am a warn!')
// console.error('I am a error!')
// console.debug('I am a debug!')
// console.info('I am a info!')
// var engine = require('engine.io')
// var http = require('http').createServer().listen(3000)
// var server = engine.attach(http)

// console.log('Hello World!')

// server.on('connection', function (socket) {
// console.log('User is connecting......')
// socket.on('message', function(data){
// console.log('Recv Message From User-->' + JSON.stringify(data))
// })
// socket.on('close', function(){ })
// })

var debug = require('debug')('test')

var engine = require('engine.io')
var server = engine.listen(3000)

var http = require('http')

server.on('connection', function(socket){
	console.log('connecting....')
	socket.send('utf 8 string')
	socket.send(new Buffer([0, 1, 2, 3, 4, 5])) // binary data

	socket.on('message', function(data){
		console.log('Recv Message From User-->' + data)
		post(data, socket)
	})

	socket.on('close', function(code){
		console.log('CLOSE: code:' + code)
	})
})

var post = function(msg, socket){
	var postData = {
		'key': 'xxxxxxxxx',
		'info': msg,
		'userid':'123456'
	}

	var options = {
		hostname: 'www.tuling123.com',
		port: 80,
		path: '/openapi/api',
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		}
	}

	var req = http.request(options, (res) => {
		console.log(`STATUS: ${res.statusCode}`)
		console.log(`HEADERS: ${JSON.stringify(res.headers)}`)
		res.setEncoding('utf8')
		res.on('data', (chunk) => {
			console.log(`BODY: ${chunk}`)
			var data = JSON.parse(chunk)
			switch (data.code){
				case 100000:
					socket.send('水帝：' + data.text)
					console.log('Send Message Success!')
					break
				case 200000:
					socket.send('水帝：' + data.text)
					socket.send('具体信息请转到此链接:' + data.url)
					break
				case 302000:
					break
				case 308000:
					break
				case 313000:
					break
				case 314000:
					break
				default:
					socket.send('水帝：水的太厉害了，水不动了。。。')
					break
			}
		})
		res.on('end', () => {
			console.log('No more data in response.')
		})
	})

	req.on('error', (e) => {
		console.log(`problem with request: ${e.message}`)
	})

	// write data to request body
	req.write(JSON.stringify(postData))
	req.end()

}
