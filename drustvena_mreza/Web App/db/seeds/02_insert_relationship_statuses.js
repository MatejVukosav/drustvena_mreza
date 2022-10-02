
exports.seed = function(knex, Promise) {
  return Promise.join(

      knex('relationship_status').insert({description : 'Single'}),
      knex('relationship_status').insert({description : 'In a relationship'}),
      knex('relationship_status').insert({description : 'Complicated'}),
      knex('relationship_status').insert({description : 'Other'})

  );
};
