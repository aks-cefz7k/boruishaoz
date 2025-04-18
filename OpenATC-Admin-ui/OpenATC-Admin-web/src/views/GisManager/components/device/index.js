import Vue from 'vue'
import XRDDirSelector from '@/components/XRDDirSelector'

let PhaseMarker = Vue.extend(XRDDirSelector)
Vue.component('phase-marker', PhaseMarker)
export default PhaseMarker
