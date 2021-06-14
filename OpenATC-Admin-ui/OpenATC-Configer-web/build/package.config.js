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
const path = require('path')
const webpack = require('webpack')

module.exports = {
  entry: {
    'edgelib': './src/packge/index.js'
  },
  output: {
    path: path.resolve(__dirname, '../package'),
    publicPath: '/package/',
    library: 'edgelib',
    libraryTarget: 'umd',
    umdNamedDefine: true
  },
  // externals: {
  //   vue: {
  //     root: 'Vue',
  //     commonjs: 'vue',
  //     commonjs2: 'vue',
  //     amd: 'vue'
  //   },
  //   'vue-router':'VueRouter',
  //   "ElementUI": 'element-ui'
  // },
  resolve: {
    extensions: ['.js', '.vue'],
    alias: {
      '@': path.join(__dirname, '..', 'src')
    }
  },
  module: {
    loaders: [{
      test: /\.vue$/,
      loader: 'vue-loader',
      options: {
        loaders: {
          css: 'vue-style-loader!css-loader',
          sass: 'vue-style-loader!css-loader!sass-loader'
        },
        postLoaders: {
          html: 'babel-loader'
        }
      }
    }, {
      test: /\.js$/,
      loader: 'babel-loader',
      include: /node_modules/
    }, {
      test: /\.css$/,
      use: [
        'style-loader',
        'css-loader',
        'autoprefixer-loader'
      ]
    }, {
      test: /\.(gif|jpg|png|woff|svg|eot|ttf)\??.*$/,
      loader: 'url-loader?limit=100000'
    }]
  },
  plugins: [
    new webpack.optimize.ModuleConcatenationPlugin()
  ]
}
