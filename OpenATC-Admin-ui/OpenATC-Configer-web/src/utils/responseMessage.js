import i18n from '@/i18n/index.js'
import en from '../i18n/language/en'
import zh from '../i18n/language/zh'

function getMessageByCode (code, langusge) {
  let res = i18n.t('edge.message.errorcode') + ' ' + code
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
