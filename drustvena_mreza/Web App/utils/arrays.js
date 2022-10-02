/**
 * Created by Domagoj on 5.12.2015..
 */
function rangeCopy(source, target, from, size){

    var working = source.slice(from, from + size);
    working.forEach(function(entry){
        target.push(entry.attributes);
    });
}

module.exports = {
    rangeCopy: rangeCopy,
}