// Update with your config settings.

module.exports = {
  development: {
      client: 'mysql',
      debug:false,
      connection: {
          filename: './dev.mysql',
          host: 'localhost',
          user: 'root',
          password: 'bazapass',
          database: 'drustvena_mreza',
          charset: 'utf8',
          port: '3306'
      },
      migrations: {
          directory: './db/migrations'
      },
      seeds: {
          directory: './db/seeds'
      }
  }
};