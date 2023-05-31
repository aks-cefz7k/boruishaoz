import Authapi from './authapi'

class Systemconfig {
  GetAllSystemconfig () {
    // 获取所有配置
    let api = new Authapi('get_all_systemconfig')
    return api.Send({}, {}, [])
  }
  GetSystemconfigByModule (module) {
    // 根据模块获取配置
    let api = new Authapi('get_systemconfig_by_module')
    return api.Send({}, {}, [module])
  }
  AddSystemconfig (data) {
    // 添加配置
    let api = new Authapi('add_systemconfig')
    return api.Send({}, data, [])
  }
  UpdateSystemconfig (data) {
    // 更新配置
    let api = new Authapi('update_systemconfig')
    return api.Send({}, data, [])
  }
  DeleteSystemconfigById (id) {
    // 删除配置
    let api = new Authapi('delete_systemconfig')
    let paramList = [id]
    return api.Send({}, {}, paramList)
  }
}

export const SystemconfigApi = new Systemconfig()
