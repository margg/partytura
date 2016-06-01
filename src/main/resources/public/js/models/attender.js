define([
    'jquery',
    'underscore',
    'backbone'
], function($, _, Backbone) {
    var Attender = Backbone.Model.extend({
        defaults: {
            'username': '',
            'twitterId': '',
            'followedInstitutions': null,
            'joinedEvents': null
        },

        url: function() {
            //todo: change url
            return "/api/attender/" + this.get('id');
        }
    });

    return Attender;

});
