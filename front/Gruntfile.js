module.exports = function(grunt) {
  grunt.loadNpmTasks("grunt-contrib-concat");
  grunt.loadNpmTasks("grunt-contrib-uglify");
  grunt.loadNpmTasks("grunt-contrib-connect");
  grunt.loadNpmTasks("grunt-karma");
  grunt.loadNpmTasks("grunt-contrib-sass");
  grunt.loadNpmTasks("grunt-shell");
  grunt.loadNpmTasks("grunt-contrib-watch");
  grunt.loadNpmTasks("grunt-contrib-jshint");
  grunt.loadNpmTasks("grunt-contrib-yuidoc");

  grunt.initConfig({
    appbase: "app",
    pkg: grunt.file.readJSON("package.json"),
    banner: "/*!\n" +
            " * <%= pkg.name %>\n" +
            " * @author <%= pkg.author %>\n" +
            " * @version <%= pkg.version %>\n" +
            " * Copyright <%= pkg.copyright %>\n" +
            " */\n",
    shell: {
      cleanDist: {
        command: "rm -R dist/*"
      }
    },
    concat: {
      scripts: {
        options: {
          separator: ";"
        },
        dest: "<%= appbase %>/js/app-compiled.js",
        src: [
          "<%= appbase %>/js/dev/*.js",
          "<%= appbase %>/js/dev/**/*.js",
        ],
      }
    },
    uglify: {
      target: {
        files: {
          "<%= appbase %>/js/app.min.js": ["<%= appbase %>/js/app-compiled.js"]
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
          "<%= appbase %>/css/styles.css": "<%= appbase %>/scss/styles.scss"
        }
      }
    },
    jshint: {
      all: [
        "<%= appbase %>/js/dev/*.js",
        "<%= appbase %>/js/dev/**/*.js",
      ]
    },
    connect: {
      server: {
        options: {
          hostname: "",
          port: 8888,
          keepalive: true,
          base: {
            path: "<%= appbase %>",
            options: {
              index: "index.html"
            }
          }
        }
      }
    },
    karma: {
      unit: {
        configFile: "test/karma-unit.conf.js",
        autoWatch: false,
        singleRun: true
      },
      unit_auto: {
        configFile: "test/karma-unit.conf.js",
        autoWatch: true,
        singleRun: false
      }
    },
    watch: {
      scripts: {
        files: [
          "<%= appbase %>/js/dev/*.js",
          "<%= appbase %>/js/dev/**/*.js"
        ],
        tasks: ["concat", "uglify:target", "docs"]
      },
      scss: {
        files: ["<%= appbase %>/scss/*.scss"],
        tasks: ["sass:dev"]
      }
    },
    yuidoc: {
      all: {
        name: "<%= pkg.name %>",
        description: "<%= pkg.description %>",
        version: "<%= pkg.version %>",
        url: "<%= pkg.homepage %>",
        options: {
          paths: ["<%= appbase %>/js/dev"],
          outdir: "docs"
        }
      }
    }
  });

  grunt.registerTask("clean", ["shell:cleanDist"]);
  grunt.registerTask("build", ["sass:dev", "concat", "uglify:target"]);
  grunt.registerTask("run", ["connect:server"]);
  grunt.registerTask("docs", ["yuidoc"]);
  grunt.registerTask("test", ["karma:unit_auto"]);
};
