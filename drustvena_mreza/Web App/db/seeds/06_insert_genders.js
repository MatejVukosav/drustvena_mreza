
exports.seed = function(knex, Promise) {
  return Promise.join(

      knex('gender').insert({type: 'Female'}),
      knex('gender').insert({type: 'Male'})

  );
};
