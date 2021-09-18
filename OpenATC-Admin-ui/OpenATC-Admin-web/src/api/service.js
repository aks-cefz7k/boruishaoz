// 路口执勤
import Authapi from './authapi'

export const GetAllBriefViproutes = () => {
  let api = new Authapi('getAllBriefViproutes')

  return api.Send({}, {}, [])
}

export const GetAllViproutes = () => {
  let api = new Authapi('getAllViproutes')
  let paramList = []
  return api.Send({}, {}, paramList)
}

export const GetSingleViproute = (id) => {
  let api = new Authapi('getSingleViproute')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export const AddViproute = (data) => {
  let api = new Authapi('addViproute')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const UpdateViproute = (data) => {
  let api = new Authapi('updateViproute')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const DeleteViproute = (id) => {
  let api = new Authapi('deleteViproute')

  let paramList = [id]

  return api.Send({}, {}, paramList)
}

export const ExecuteViproute = (param) => {
  let api = new Authapi('executeViproute')
  return api.Send({}, param)
}

export const RenameViproute = (data) => {
  let api = new Authapi('renameViproute')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const GetViprouteStatus = (id) => {
  let api = new Authapi('getViprouteStatus')

  let paramList = [id, 'status']

  return api.Send({}, {}, paramList)
}
