
exports.seed = function(knex, Promise) {
  return Promise.join(

      knex('content_type').insert({id: 1, description : 'post'}),
      knex('content_type').insert({id: 2, description : 'image'})

  );
};
