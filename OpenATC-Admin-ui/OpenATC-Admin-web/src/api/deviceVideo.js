// 设备视频接口
import Authapi from './authapi'

export const GetAllVideos = () => {
  let api = new Authapi('GetAllVideos')
  return api.Send({}, {}, [])
}

export const GetRouteVideos = (ids) => {
  let api = new Authapi('GetRouteVideos')
  const params = {
    videoList: ids
  }
  return api.Send({}, params, [])
}

export const GetCrossVideos = (data) => {
  let api = new Authapi('GetCrossVideos')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, {}, params)
}

export const AddDeviceVideo = (data) => {
  let api = new Authapi('AddDeviceVideo')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}
export const UpdateDeviceVideo = (data) => {
  let api = new Authapi('UpdateDeviceVideo')
  const params = JSON.parse(JSON.stringify(data))
  return api.Send({}, params, [])
}

export const DeleteDeviceVideo = (id) => {
  let api = new Authapi('DeleteDeviceVideo')

  let paramList = [id]

  return api.Send({}, {}, paramList)
}
