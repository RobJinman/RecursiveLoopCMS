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
    appResources: "src/main/webapp/resources",
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
        dest: "<%= appResources %>/default/js/dist/app-compiled.js",
        src: [
          "<%= appResources %>/default/js/dev/**/*.js",
        ],
      }
    },
    uglify: {
      target: {
        files: {
          "<%= appResources %>/default/js/dist/app.min.js": ["<%= appResources %>/default/js/dist/app-compiled.js"]
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
          "<%= appResources %>/default/css/styles.css": "<%= appResources %>/default/scss/styles.scss"
        }
      }
    },
    jshint: {
      all: [
        "<%= appResources %>/default/js/dev/**/*.js",
      ]
    },
    karma: {
      unit: {
        configFile: "<%= appResources %>/default/js/test/karma-unit.conf.js",
        autoWatch: false,
        singleRun: true
      },
      unit_auto: {
        configFile: "<%= appResources %>/default/js/test/karma-unit.conf.js",
        autoWatch: true,
        singleRun: false
      }
    },
    watch: {
      js: {
        files: [
          "<%= appResources %>/default/js/dev/**/*.js"
        ],
        tasks: ["concat", "uglify:target", "docs"]
      },
      js_compiled: {
        files: [
          "<%= appResources %>/default/js/dist/**/*.js",
        ],
        tasks: ["copy:js"]
      },
      scss: {
        files: [
          "<%= appResources %>/default/scss/**/*.scss"
        ],
        tasks: ["sass:dev"]
      },
      css: {
        files: [
          "<%= appResources %>/default/css/**/*.css"
        ],
        tasks: ["copy:css"]
      },
      img: {
        files: [
          "<%= appResources %>/default/img/**"
        ],
        tasks: ["copy:img"]
      },
      xhtml: {
        files: [
          "<%= appBase %>/**/*.xhtml"
        ],
        tasks: ["copy:xhtml"]
      },
      components: {
        files: [
          "<%= appResources %>/components/**/*.xhtml",
        ],
        tasks: ["copy:components"]
      },
      java: {
        files: [
          "build/classes/main/**"
        ],
        options: {
          event: ['added', 'changed'],
        },
        tasks: ["copy:java", "shell:glassfishReload"]
      }
    },
    copy: {
      css: {
        expand: true,
        cwd: "<%= appResources %>/default/css/",
        src: "**",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/resources/default/css/"
      },
      components: {
        expand: true,
        cwd: "<%= appResources %>/components/",
        src: "**",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/resources/components/"
      },
      xhtml: {
        expand: true,
        cwd: "<%= appBase %>/",
        src: "**",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/"
      },
      js: {
        expand: true,
        cwd: "<%= appResources %>/default/js/dist/",
        src: "**",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/resources/default/js/dist/"
      },
      img: {
        expand: true,
        cwd: "<%= appResources %>/default/img/",
        src: "**",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/resources/default/img/"
      },
      java: {
        expand: true,
        cwd: "build/classes/main/",
        src: "**",
        dest: "/tmp/cargo/conf/cargo-domain/applications/ROOT/WEB-INF/classes/"
      }
    },
    shell: {
      glassfishReload: {
        command: "touch /tmp/cargo/conf/cargo-domain/applications/ROOT/.reload"
      }
    },
    yuidoc: {
      all: {
        name: "<%= pkg.name %>",
        description: "<%= pkg.description %>",
        version: "<%= pkg.version %>",
        url: "<%= pkg.homepage %>",
        options: {
          paths: ["<%= appResources %>/default/js/dev"],
          outdir: "docs"
        }
      }
    }
  });

  grunt.registerTask("build", ["sass:dev", "concat", "uglify:target"]);
  grunt.registerTask("docs", ["yuidoc"]);
  grunt.registerTask("test", ["karma:unit_auto"]);
};
