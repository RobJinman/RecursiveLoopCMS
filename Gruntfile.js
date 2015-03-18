module.exports = function(grunt) {
  grunt.loadNpmTasks("grunt-contrib-copy");
  grunt.loadNpmTasks("grunt-contrib-concat");
  grunt.loadNpmTasks("grunt-contrib-uglify");
  grunt.loadNpmTasks("grunt-karma");
  grunt.loadNpmTasks("grunt-contrib-sass");
  grunt.loadNpmTasks("grunt-shell");
  grunt.loadNpmTasks("grunt-contrib-watch");
  grunt.loadNpmTasks("grunt-contrib-jshint");
  grunt.loadNpmTasks("grunt-contrib-yuidoc");

  grunt.initConfig({
    appBase: "src/main/webapp",
    appResources: "src/main/webapp/resources/default",
    pkg: grunt.file.readJSON("package.json"),
    banner: "/*!\n" +
            " * <%= pkg.name %>\n" +
            " * @author <%= pkg.author %>\n" +
            " * @version <%= pkg.version %>\n" +
            " * Copyright <%= pkg.copyright %>\n" +
            " */\n",
    concat: {
      scripts: {
        options: {
          separator: ";"
        },
        dest: "<%= appResources %>/js/dist/app-compiled.js",
        src: [
          "<%= appResources %>/js/dev/*.js",
          "<%= appResources %>/js/dev/**/*.js",
        ],
      }
    },
    uglify: {
      target: {
        files: {
          "<%= appResources %>/js/dist/app.min.js": ["<%= appResources %>/js/dist/app-compiled.js"]
        }
      }
    },
    sass: {
      dev: {
        options: {
          style: "expanded",
          banner: "<%= banner %>",
        },
        files: {
          "<%= appResources %>/css/styles.css": "<%= appResources %>/scss/styles.scss"
        }
      }
    },
    jshint: {
      all: [
        "<%= appResources %>/js/dev/*.js",
        "<%= appResources %>/js/dev/**/*.js",
      ]
    },
    karma: {
      unit: {
        configFile: "<%= appResources %>/js/test/karma-unit.conf.js",
        autoWatch: false,
        singleRun: true
      },
      unit_auto: {
        configFile: "<%= appResources %>/js/test/karma-unit.conf.js",
        autoWatch: true,
        singleRun: false
      }
    },
    watch: {
      js: {
        files: [
          "<%= appResources %>/js/dev/*.js",
          "<%= appResources %>/js/dev/**/*.js"
        ],
        tasks: ["concat", "uglify:target", "docs"]
      },
      js_compiled: {
        files: [
          "<%= appResources %>/js/dist/*.js",
          "<%= appResources %>/js/dist/**/*.js",
        ],
        tasks: ["copy:js"]
      },
      scss: {
        files: [
          "<%= appResources %>/scss/*.scss",
          "<%= appResources %>/scss/**/*.scss"
        ],
        tasks: ["sass:dev"]
      },
      css: {
        files: [
          "<%= appResources %>/css/*.css",
          "<%= appResources %>/css/**/*.css"
        ],
        tasks: ["copy:css"]
      },
      xhtml: {
        files: [
          "<%= appBase %>/**/*.xhtml"
        ],
        tasks: ["copy:xhtml"]
      }
    },
    copy: {
      css: {
        expand: true,
        cwd: "<%= appResources %>/css/",
        src: "**/*",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/resources/default/css/"
      },
      xhtml: {
        expand: true,
        cwd: "<%= appBase %>/",
        src: "**/*",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/"
      },
      js: {
        expand: true,
        cwd: "<%= appResources %>/js/dist/",
        src: "**/*",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/resources/default/js/dist/"
      }
    },
    yuidoc: {
      all: {
        name: "<%= pkg.name %>",
        description: "<%= pkg.description %>",
        version: "<%= pkg.version %>",
        url: "<%= pkg.homepage %>",
        options: {
          paths: ["<%= appResources %>/js/dev"],
          outdir: "docs"
        }
      }
    }
  });

  grunt.registerTask("build", ["sass:dev", "concat", "uglify:target"]);
  grunt.registerTask("docs", ["yuidoc"]);
  grunt.registerTask("test", ["karma:unit_auto"]);
};
