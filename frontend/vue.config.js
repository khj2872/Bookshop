module.exports = {
  transpileDependencies: ['vuex-persist'],
  publicPath: '',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
}