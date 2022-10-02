
exports.seed = function(knex, Promise) {
  return Promise.join(

      knex('bubble_type').insert({description : 'timeline'}),
      knex('bubble_type').insert({description: 'gallery'}),
      knex('bubble_type').insert({description: 'created'})

  );
};
