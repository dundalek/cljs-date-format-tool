const moment = require('moment')
const _ = require('lodash')
const edn = require('jsedn')

// http://momentjs.com/docs/#/displaying/
// copy(JSON.stringify([].map.call($0.querySelectorAll('tr > td:nth-child(2)'), (el) => el.textContent)))
let fmts = "M Mo MM MMM MMMM Q Qo D Do DD DDD DDDo DDDD d do dd ddd dddd e E w wo ww W Wo WW YY YYYY Y gg gggg GG GGGG A a H HH h hh k kk m mm s ss S SS SSS SSSS SSSSS SSSSSS SSSSSSS SSSSSSSS SSSSSSSSS z or zz Z ZZ X x".split(' ')

const m = moment('1994-02-03T07:08:09.006')

let r;

fmts = _(fmts)
  .difference(['e', 'E', 'GG', 'GGGG', 'gg', 'gggg', 'Y', 'z', 'zz'])
  .map(key => ({ key, val: m.format(key) }))
  .groupBy('val')
  // .filter(val => val.length > 1)
  .map(x => x[0])
  .orderBy(x => -x.val.length)
  .value()

const d1 = '03.02.1994 07:08';
const d2 = '02/03/1994 7:08 am';

function guesstimate(str) {
  const ret = [];
  while (str) {
    let i;
    for (i = 0; i < fmts.length; i += 1) {
      const fmt = fmts[i];
      if (str.slice(0, fmt.val.length) === fmt.val) {
        ret.push(fmt.key);
        str = str.slice(fmt.val.length);
        break
      }
    }
    if (i === fmts.length) {
      ret.push(str[0]);
      str = str.slice(1);
    }
  }
  return ret.join('');
}

// console.log(fmts)
console.log(edn.encode(fmts))
console.log(guesstimate(d2));
