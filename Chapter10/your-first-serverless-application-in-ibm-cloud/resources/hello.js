function main(params) {
    var name = params.name || 'World';
    return {payload:  'Hello, ' + name + '!'};
}