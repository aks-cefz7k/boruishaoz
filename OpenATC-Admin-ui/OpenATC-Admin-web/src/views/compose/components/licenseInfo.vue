/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
<template>
  <div class="licenseInfo">
    <el-dialog
      title="开源协议"
      :visible.sync="licenseInfoVisible"
      width="60%"
      height="914px"
      @close='closeFormDialog'>
      <div>
        <div class="content-container chinese-version">
                            <h2 class="title">木兰宽松许可证, 第2版</h2>
                            <div class="content">
                                <p><strong>木兰宽松许可证， 第2版</strong></p>
                                <p style="margin-bottom: 20px">2020年1月 <a href="http://license.coscl.org.cn/MulanPSL2">http://license.coscl.org.cn/MulanPSL2</a></p>

                                <p style="text-indent: 2em">您对“软件”的复制、使用、修改及分发受木兰宽松许可证，第2版（“本许可证”）的如下条款的约束：</p>

                                <div style="text-indent: 2em">
                                    <p><strong>0.&nbsp;&nbsp;&nbsp;定义</strong></p>
                                    <p><strong>“软件”</strong> 是指由“贡献”构成的许可在“本许可证”下的程序和相关文档的集合。</p>
                                    <p><strong>“贡献”</strong> 是指由任一“贡献者”许可在“本许可证”下的受版权法保护的作品。</p>
                                    <p><strong>“贡献者”</strong> 是指将受版权法保护的作品许可在“本许可证”下的自然人或“法人实体”。</p>
                                    <p><strong>“法人实体”</strong> 是指提交贡献的机构及其“关联实体”。</p>
                                    <p><strong>“关联实体”</strong> 是指，对“本许可证”下的行为方而言，控制、受控制或与其共同受控制的机构，此处的控制是指有受控方或共同受控方至少50%直接或间接的投票权、资金或其他有价证券。</p>
                                </div>

                                <div style="text-indent: 2em">
                                    <p><strong>1.&nbsp;&nbsp;&nbsp;授予版权许可</strong></p>
                                    <p>每个“贡献者”根据“本许可证”授予您永久性的、全球性的、免费的、非独占的、不可撤销的版权许可，您可以复制、使用、修改、分发其“贡献”，不论修改与否。</p>
                                </div>

                                <div style="text-indent: 2em">
                                    <p><strong>2.&nbsp;&nbsp;&nbsp;授予专利许可</strong></p>
                                    <p>每个“贡献者”根据“本许可证”授予您永久性的、全球性的、免费的、非独占的、不可撤销的（根据本条规定撤销除外）专利许可，供您制造、委托制造、使用、许诺销售、销售、进口其“贡献”或以其他方式转移其“贡献”。前述专利许可仅限于“贡献者”现在或将来拥有或控制的其“贡献”本身或其“贡献”与许可“贡献”时的“软件”结合而将必然会侵犯的专利权利要求，不包括对“贡献”的修改或包含“贡献”的其他结合。如果您或您的“关联实体”直接或间接地，就“软件”或其中的“贡献”对任何人发起专利侵权诉讼（包括反诉或交叉诉讼）或其他专利维权行动，指控其侵犯专利权，则“本许可证”授予您对“软件”的专利许可自您提起诉讼或发起维权行动之日终止。</p>
                                </div>

                                <div style="text-indent: 2em">
                                    <p><strong>3.&nbsp;&nbsp;&nbsp;无商标许可</strong></p>
                                    <p>“本许可证”不提供对“贡献者”的商品名称、商标、服务标志或产品名称的商标许可，但您为满足第4条规定的声明义务而必须使用除外。</p>
                                </div>

                                <div style="text-indent: 2em">
                                    <p><strong>4.&nbsp;&nbsp;&nbsp;分发限制</strong></p>
                                    <p>您可以在任何媒介中将“软件”以源程序形式或可执行形式重新分发，不论修改与否，但您必须向接收者提供“本许可证”的副本，并保留“软件”中的版权、商标、专利及免责声明。</p>
                                </div>

                                <div style="text-indent: 2em">
                                    <p><strong>5.&nbsp;&nbsp;&nbsp;免责声明与责任限制</strong></p>
                                    <p><strong>“软件”及其中的“贡献”在提供时不带任何明示或默示的担保。在任何情况下，“贡献者”或版权所有者不对任何人因使用“软件”或其中的“贡献”而引发的任何直接或间接损失承担责任，不论因何种原因导致或者基于何种法律理论，即使其曾被建议有此种损失的可能性。</strong></p>
                                </div>

                                <div style="text-indent: 2em">
                                    <p><strong>6.&nbsp;&nbsp;&nbsp;语言</strong></p>
                                    <p><strong>“本许可证”以中英文双语表述，中英文版本具有同等法律效力。如果中英文版本存在任何冲突不一致，以中文版为准。</strong></p>
                                </div>

                                <p style="margin-bottom: 20px"><strong>条款结束</strong></p>
                            </div>
                        </div>

                        <div class="content-container english-version">
                            <h2 class="title">Mulan Permissive Software License，Version 2</h2>
                            <div class="content">
                                <p><strong>Mulan Permissive Software License，Version 2 (Mulan PSL v2)</strong></p>
                                <p style="margin-bottom: 20px">January 2020 <a href="http://license.coscl.org.cn/MulanPSL2">http://license.coscl.org.cn/MulanPSL2</a></p>

                                <p>Your reproduction, use, modification and distribution of the Software shall be subject to Mulan PSL v2 (this License) with the following terms and conditions:</p>
                                <p><strong>0.&nbsp;Definition</strong></p>
                                <div>
                                    <p><strong>Software</strong> means the program and related documents which are licensed under this License and comprise all Contribution(s).</p>
                                    <p><strong>Contribution</strong> means the copyrightable work licensed by a particular Contributor under this License.</p>
                                    <p><strong>Contributor</strong> means the Individual or Legal Entity who licenses its copyrightable work under this License.</p>
                                    <p><strong>Legal Entity</strong> means the entity making a Contribution and all its Affiliates.</p>
                                    <p><strong>Affiliates</strong> means entities that control, are controlled by, or are under common control with the acting entity under this License, ‘control’ means direct or indirect ownership of at least fifty percent (50%) of the voting power, capital or other securities of controlled or commonly controlled entity.</p>
                                </div>

                                <p><strong>1.&nbsp;Grant of Copyright License</strong></p>
                                <div>
                                    <p>Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable copyright license to reproduce, use, modify, or distribute its Contribution, with modification or not.</p>
                                </div>

                                <p><strong>2.&nbsp;Grant of Patent License</strong></p>
                                <div>
                                    <p>Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable (except for revocation under this Section) patent license to make, have made, use, offer for sale, sell, import or otherwise transfer its Contribution, where such patent license is only limited to the patent claims owned or controlled by such Contributor now or in future which will be necessarily infringed by its Contribution alone, or by combination of the Contribution with the Software to which the Contribution was contributed. The patent license shall not apply to any modification of the Contribution, and any other combination which includes the Contribution. If you or your Affiliates directly or indirectly institute patent litigation (including a cross claim or counterclaim in a litigation) or other patent enforcement activities against any individual or entity by alleging that the Software or any Contribution in it infringes patents, then any patent license granted to you under this License for the Software shall terminate as of the date such litigation or activity is filed or taken.</p>
                                </div>

                                <p><strong>3.&nbsp;No Trademark License</strong></p>
                                <div>
                                    <p>No trademark license is granted to use the trade names, trademarks, service marks, or product names of Contributor, except as required to fulfill notice requirements in section 4.</p>
                                </div>

                                <p><strong>4.&nbsp;Distribution Restriction</strong></p>
                                <div>
                                    <p>You may distribute the Software in any medium with or without modification, whether in source or executable forms, provided that you provide recipients with a copy of this License and retain copyright, patent, trademark and disclaimer statements in the Software.</p>
                                </div>

                                <p><strong>5.&nbsp;Disclaimer of Warranty and Limitation of Liability</strong></p>
                                <div>
                                    <p>THE SOFTWARE AND CONTRIBUTION IN IT ARE PROVIDED WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED. IN NO EVENT SHALL ANY CONTRIBUTOR OR COPYRIGHT HOLDER BE LIABLE TO YOU FOR ANY DAMAGES, INCLUDING, BUT NOT LIMITED TO ANY DIRECT, OR INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING FROM YOUR USE OR INABILITY TO USE THE SOFTWARE OR THE CONTRIBUTION IN IT, NO MATTER HOW IT’S CAUSED OR BASED ON WHICH LEGAL THEORY, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.</p>
                                </div>

                                <p><strong>6.&nbsp;Language</strong></p>
                                <div>
                                    <p>THIS LICENSE IS WRITTEN IN BOTH CHINESE AND ENGLISH, AND THE CHINESE VERSION AND ENGLISH VERSION SHALL HAVE THE SAME LEGAL EFFECT. IN THE CASE OF DIVERGENCE BETWEEN THE CHINESE AND ENGLISH VERSIONS, THE CHINESE VERSION SHALL PREVAIL.</p>
                                </div>

                                <p style="margin-bottom: 20px"><strong>END OF THE TERMS AND CONDITIONS</strong></p>
                            </div>
                        </div></div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: yangdongyang
  Created: 2020/09/10
*/
export default {
  data () {
    return {
      licenseInfoVisible: false
    }
  },
  name: 'licenseInfo',
  props: {},
  watch: {
    deep: true
  },
  methods: {
    showMessage () {
      this.licenseInfoVisible = !this.licenseInfoVisible
    },
    closeFormDialog () {
      this.licenseInfoVisible = false
    }
  }
}
</script>

<style lang="scss" rel="stylesheet/scss">
.licenseInfo {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
    .el-dialog {
        height: 70%;
        overflow: hidden;
        .el-dialog__body {
            padding: 30px 20px;
            color: $--color-text-regular;
            font-size: 14px;
            word-break: break-all;
            position: absolute;
            left: 0;
            top: 70px;
            bottom: 30px;
            z-index: 1;
            overflow: hidden;
            overflow-y: auto;
        }
    }
}
.licenseInfo .el-dialog__headerbtn .el-dialog__close {
    color: $--color-white;
}
.licenseInfo .el-dialog__header {
    padding: 30px 10px 10px;
    height: 69px;
    background-color: $--color-primary;
    border-radius: 4px 4px 0px 0p
}
.licenseInfo .el-dialog__title {
    font-family: MicrosoftYaHei;
    font-size: 20px;
    font-weight: normal;
    font-stretch: normal;
    line-height: 14px;
    letter-spacing: 0px;
    color: $--color-white;
}
</style>
