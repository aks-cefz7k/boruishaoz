const webpack = require('webpack')
const merge = require('webpack-merge')
const path = require('path')
const utils = require('./utils')
const vueLoaderConfig = require('./vue-loader.conf')
const config = require('../config')
const baseWebpackConfig = require('./package.config')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const extractScss = new ExtractTextPlugin('/kissui.min.css')
const OptimizeCSSPlugin = require('optimize-css-assets-webpack-plugin')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')

function resolve(dir) {
  return path.join(__dirname, '..', dir)
}
const createLintingRule = () => ({
  test: /\.(js|vue)$/,
  loader: 'eslint-loader',
  enforce: 'pre',
  include: [resolve('src'), resolve('test')],
  options: {
    formatter: require('eslint-friendly-formatter'),
    emitWarning: !config.dev.showEslintErrorsInOverlay
  }
})

module.exports = merge(baseWebpackConfig, {
  output: {
    filename: '[name].min.js'
  },
  module: {
    rules: [
      ...(config.dev.useEslint ? [createLintingRule()] : []),
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: vueLoaderConfig
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        include: [resolve('src'), resolve('test'), resolve('node_modules/webpack-dev-server/client'),
          resolve('../src/lib')
        ]
      },
      {
        test: /\.svg$/,
        loader: 'svg-sprite-loader',
        include: [resolve('src/icons')],
        options: {
          symbolId: 'icon-[name]'
        }
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        exclude: [resolve('src/icons')],
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('media/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.css$/,
        use: [
          //'sass-loader',
          'css-loader',
          //'autoprefixer-loader'
        ]
      },
      {
        test: /\.scss$/,
        use: [
          'style-loader',
          'css-loader',
          'sass-loader'
        ]
      },
    ],
    loaders: [{
      test: /\.scss$/,
      loader: extractScss.extract(['css-loader', 'sass-loader'])
    }]
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery',
      jquery: 'jquery',
      'window.jQuery': 'jquery'
    }),
    // new webpack.optimize.OccurrenceOrderPlugin(),
    // new webpack.optimize.UglifyJsPlugin({
    //   uglifyOptions: {
    //     ie8: false,
    //     output: {
    //       comments: false,
    //       beautify: false
    //     },
    //     mangle: {
    //       keep_fnames: true
    //     },
    //     compress: {
    //       warnings: false,
    //       drop_console: true
    //     }
    //   }
    // }),
    new UglifyJsPlugin({
      uglifyOptions: {
        ie8: false,
        output: {
          comments: false,
          beautify: false
        },
        mangle: {
          keep_fnames: true
        },
        compress: {
          warnings: false,
          drop_console: true
        }
      },
      sourceMap: config.build.productionSourceMap,
      parallel: true
    }),
    extractScss,
    new OptimizeCSSPlugin({
      cssProcessorOptions: config.build.productionSourceMap
        ? { safe: true, map: { inline: false } }
        : { safe: true }
    }),
    new CopyWebpackPlugin([
      // {output}/file.txt
      {from: `./src/kisscomps`, to: `./kisscomps`}
    ])
  ]
})
