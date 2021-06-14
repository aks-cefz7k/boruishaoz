/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
/* eslint-disable new-cap */
import authapi from './authapi'

export const GetOrganizationList = () => {
  let api = new authapi('getOrganization')
  return api.Send({}, {})
}

export const GetOrganizationById = (id) => {
  let api = new authapi('getOrganization')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export const AddOrganization = (path, description) => {
  let api = new authapi('addOrganization')
  let data = {
    'orgnization_code': path,
    'description': description,
    'status': 0
  }
  return api.Send({}, data)
}

export const UpdateOrganization = (id, path, description) => {
  let api = new authapi('addOrganization')
  let data = {
    'orgnization_code': path,
    'description': description,
    'status': 0,
    'id': id
  }
  return api.Send({}, data)
}

export const DeleteOrganization = id => {
  let api = new authapi('delOrganization')
  let paramList = [id]
  return api.Send({}, {}, paramList)
}

export default {
  GetOrganizationList: GetOrganizationList,
  GetOrganizationById: GetOrganizationById,
  AddOrganization: AddOrganization,
  UpdateOrganization: UpdateOrganization,
  DeleteOrganization: DeleteOrganization
}
