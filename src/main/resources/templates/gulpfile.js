var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglifyjs');
var jshint = require('gulp-jshint');
var sass = require('gulp-sass');
var minifyCss = require('gulp-minify-css');
var browserSync = require('browser-sync').create();
//var rjs = require('gulp-requirejs');


/*gulp.task('requirejs', function () {
    rjs({
        name: 'main',
        baseUrl: 'dev/js',
        out: 'main.js',
        paths: {
            jquery: 'lib/jquery'
        }
    })
});*/

gulp.task('minify-css', function () {
    gulp.src('dev/css/*.css')
        .pipe(minifyCss({compatibility: 'ie8'}))
        .pipe(concat('main.min.css'))
        .pipe(gulp.dest('assets/css'));
});

gulp.task('sass', function () {
    gulp.src('dev/scss/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(sass({outputStyle: 'compressed'}))
        .pipe(sass())
        .pipe(gulp.dest('dev/css'));
});

gulp.task('default', ['browser-sync']);

gulp.task('uglify', function () {
    gulp.src('dev/js/**/*.js')
        .pipe(uglify('main.min.js'))
        .pipe(gulp.dest('assets/js'))
});

gulp.task('hint', function () {
    gulp.src(['dev/js/**/*.js', '!dev/js/lib/**/*.js'])
        .pipe(jshint())
        .pipe(jshint.reporter('default', {verbose: true}));
});

gulp.task('browser-sync', function () {
    browserSync.init({
        server: {
            baseDir: "./"
        }
    });

    gulp.watch("dev/scss/*.scss", ['sass', 'minify-css']).on('change', browserSync.reload);
    gulp.watch("dev/js/**/*.js", ['hint', 'uglify']).on('change', browserSync.reload);
    gulp.watch("./*.html").on('change', browserSync.reload);
});