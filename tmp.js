const _ = require('lodash')
const edn = require('jsedn')

/* ==== moment.js ==== */

const moment = require('moment')
// http://momentjs.com/docs/#/displaying/
// copy(JSON.stringify([].map.call($0.querySelectorAll('tr > td:nth-child(2)'), (el) => el.textContent)))
let momentSymbols = "M Mo MM MMM MMMM Q Qo D Do DD DDD DDDo DDDD d do dd ddd dddd e E w wo ww W Wo WW YY YYYY Y gg gggg GG GGGG A a H HH h hh k kk m mm s ss S SS SSS SSSS SSSSS SSSSSS SSSSSSS SSSSSSSS SSSSSSSSS z or zz Z ZZ X x".split(' ')
momentSymbols = _.difference(momentSymbols, ['e', 'E', 'GG', 'GGGG', 'gg', 'gggg', 'Y', 'z', 'zz']);

function momentFormat(date, fmt) {
  return moment(date).format(fmt);
}

/* ==== closure library ==== */

require('google-closure-library')
goog.require('goog.i18n.DateTimeFormat');
goog.require('goog.i18n.TimeZone');

// http://userguide.icu-project.org/formatparse/datetime
// copy([].map.call($0.querySelectorAll('tr > td:nth-child(3)'), (el) => el.textContent).join(' ').replace(/,|or|'/g, ' ').replace(/\s+/g, ' '))
let closureSymbols = _('G GG GGG GGGG GGGGG yy y yyyy Y u U r Q QQ QQQ QQQQ QQQQQ q qq qqq qqqq qqqqq M MM MMM MMMM MMMMM L LL LLL LLLL LLLLL w ww W d dd D F g E EE EEE EEEE EEEEE EEEEEE e ee eee eeee eeeee eeeeee c cc ccc cccc ccccc cccccc a H HH h hh k kk K KK m mm s ss S SS SSS SSSS A z zz zzz zzzz Z ZZ ZZZ ZZZZ ZZZZZ O OOOO v vvvv V VV VVV VVVV X XX XXX XXXX XXXXX x xx xxx xxxx xxxxx').split(' ').value();
closureSymbols = _.difference(closureSymbols, 'GG GGG yyyy EE EEE ee cc cccccc zz zzz ZZ ZZZ k kk K KK'.split(' '));
const closureTz = goog.i18n.TimeZone.createTimeZone(0);

function closureFormat(date, fmt) {
  return (new goog.i18n.DateTimeFormat(fmt)).format(date, closureTz);
}

/* ==== unix date ==== */

const execSync = require('child_process').execSync;

// man date | sed -n 's/^\s*\(%[a-zA-Z]\).*$/\1/p'
const unixDateSymbols = _('%a %A %b %B %c %C %d %D %e %F %g %G %h %H %I %j %k %l %m %M %n %N %p %P %r %R %s %S %t %T %u %U %V %w %W %x %X %y %Y %z %Z %-H %%'.split(' '))
  .difference('%u %k %l %g %G %b %n %t %U %W %r %I'.split(' '))
  .value();
// TODO: handle 12hour formats %I %r

function unixDateFormat(date, fmt) {
  return execSync(`LC_ALL=en_US.utf-8 date -u -d "${date.toISOString()}" "+${fmt}"`, {encoding: 'utf-8'}).trim();
}

/* ====  generic fns ==== */


const d = new Date('1994-02-03T07:08:09.006')
const fmtFn = closureFormat;

let fmts = _(closureSymbols)
  .map(key => ({ key, val: fmtFn(d, key) }))
  .filter(({ key, val }) => key !== val)
  .groupBy('val')
  // .filter(val => val.length > 1)
  .map(x => x[0])
  .orderBy(x => -x.val.length)
  .value()

// console.log(fmts)
console.log(edn.encode(fmts))


// const d1 = '03.02.1994 07:08';
// const d2 = '02/03/1994 7:08 am';
//
// function guesstimate(str) {
//   const ret = [];
//   while (str) {
//     let i;
//     for (i = 0; i < fmts.length; i += 1) {
//       const fmt = fmts[i];
//       if (str.slice(0, fmt.val.length) === fmt.val) {
//         ret.push(fmt.key);
//         str = str.slice(fmt.val.length);
//         break
//       }
//     }
//     if (i === fmts.length) {
//       ret.push(str[0]);
//       str = str.slice(1);
//     }
//   }
//   return ret.join('');
// }

// console.log(guesstimate(d2));
