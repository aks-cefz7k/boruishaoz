
import en from '../i18n/language/en'
import zh from '../i18n/language/zh'

function getMessageByCode (code, langusge) {
  let res = 'error'
  let message = zh.edge.message
  if (code && langusge) {
    if (langusge === 'en') {
      message = en.edge.message
    }
    if (message[code]) {
      res = message[code]
    }
  }
  return res
}

export {
  getMessageByCode
}
