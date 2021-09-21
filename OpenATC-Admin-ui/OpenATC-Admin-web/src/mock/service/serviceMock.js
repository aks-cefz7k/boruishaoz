import Authapi from '@/api/authapi'
const Mock = require('mockjs')

let key = 'getAllViproutes'
let data = require('./getAllViproutes.json')
let config = new Authapi(key).urlMgr.urlMap.get(key)
Mock.mock(config.url, config.method, data)
