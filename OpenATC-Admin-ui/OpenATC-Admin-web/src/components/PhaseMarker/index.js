import Vue from 'vue'
import PhaseM from './phasemarker.vue'

let PhaseMarker = Vue.extend(PhaseM)
Vue.component('phase-marker', PhaseMarker)
export default PhaseMarker
