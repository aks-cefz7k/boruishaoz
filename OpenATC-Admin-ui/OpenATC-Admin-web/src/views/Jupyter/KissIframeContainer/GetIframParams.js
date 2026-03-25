export default function getQueryStr (sArgName) {
  let retval
  let args = window.location.search.split('?')
  var str = args[1]
  args = str.split('&')
  for (var i = 0; i < args.length; i++) {
    str = args[i]
    var arg = str.split('=')
    if (arg.length <= 1) continue
    if (arg[0] === sArgName) retval = arg[1]
  }
  return retval
}
