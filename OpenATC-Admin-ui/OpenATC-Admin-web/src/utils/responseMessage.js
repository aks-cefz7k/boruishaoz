
import en from '../i18n/language/en'
import zh from '../i18n/language/zh'
import Edgebase from '@openatc/edgebase-front'

function getMessageByCode (code, language) {
  let res = 'error'
  let message = zh.openatc.message
  let edgebaseMessage = Edgebase.language.zh.edge.message
  if (code && language) {
    if (language === 'en') {
      message = en.openatc.message
      edgebaseMessage = Edgebase.language.en.edge.message
    }
    message = Object.assign(message, edgebaseMessage)
    if (message[code]) {
      res = message[code]
    }
  }
  return res
}

export {
  getMessageByCode
}
