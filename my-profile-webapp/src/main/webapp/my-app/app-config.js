'use strict';
define([], function() {
  return {
    paths: {
      'ngMessages': 'https://cdnjs.cloudflare.com/ajax/libs/angular-messages/1.6.0/angular-messages.min',
    },
    shims: {
      'ngMessages': {
        deps: ['angular'],
      },
    },
  };
});
