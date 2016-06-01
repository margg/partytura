define([
    'jquery',
    'underscore',
    'backbone'
], function($, _, Backbone) {
    var Event = Backbone.Model.extend({
        defaults: {
            'id': '',
            'eventName': '',
            'hashtag': '',
            'location': null,
            'dateTime': null
        },

        url: function() {
            return "/api/event/" + this.get('id');
        }
    });

    return Event;

});
