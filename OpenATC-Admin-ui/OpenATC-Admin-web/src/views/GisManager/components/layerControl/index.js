import Vue from 'vue'
import PhaseShow from '@/components/PhaseMarker'

let PhaseMarker = Vue.extend(PhaseShow)
Vue.component('phase-marker', PhaseMarker)
export default PhaseMarker
