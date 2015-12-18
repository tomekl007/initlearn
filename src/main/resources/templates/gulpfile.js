var gulp = require('gulp');
var concat = require('gulp-concat');
var uglifyify = require('uglifyify');
var sass = require('gulp-sass');
var minifyCss = require('gulp-minify-css');
var browserSync = require('browser-sync').create();
var browserify = require('browserify');
var source = require('vinyl-source-stream');
var rename = require('gulp-rename');
var babelify = require('babelify');

gulp.task('compile-js', function() {
    var b = browserify();
    b.transform(babelify);
    b.transform(uglifyify);
    b.add('dev/js/main.js');
    return b.bundle()
        .pipe(source('dev/js/main.js'))
        .pipe(rename('main.min.js'))
        .pipe(gulp.dest('assets/js/'));
});

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

gulp.task('browser-sync', function () {
    browserSync.init({
        server: {
            baseDir: "./"
        }
    });

    gulp.watch("dev/scss/*.scss", ['sass', 'minify-css']).on('change', browserSync.reload);
    gulp.watch("dev/js/**/*.js", ['compile-js']).on('change', browserSync.reload);
    gulp.watch("./*.html").on('change', browserSync.reload);
});

gulp.task('default', ['browser-sync']);