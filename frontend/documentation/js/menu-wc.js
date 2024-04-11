'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">frontend documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Properties
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#components-links"' :
                            'data-bs-target="#xs-components-links"' }>
                            <span class="icon ion-md-cog"></span>
                            <span>Components</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="components-links"' : 'id="xs-components-links"' }>
                            <li class="link">
                                <a href="components/AddEmployeesComponent.html" data-type="entity-link" >AddEmployeesComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/AddProjectComponent.html" data-type="entity-link" >AddProjectComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/AppComponent.html" data-type="entity-link" >AppComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/BenefitsComponent.html" data-type="entity-link" >BenefitsComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ConfirmationDialogComponent.html" data-type="entity-link" >ConfirmationDialogComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ContractsComponent.html" data-type="entity-link" >ContractsComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CustomTableComponent.html" data-type="entity-link" >CustomTableComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DepartmentsComponent.html" data-type="entity-link" >DepartmentsComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/EmployeeTableComponent.html" data-type="entity-link" >EmployeeTableComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/FormBuilderComponent.html" data-type="entity-link" >FormBuilderComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/GenericTableComponent.html" data-type="entity-link" >GenericTableComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ItemsMenuComponent.html" data-type="entity-link" >ItemsMenuComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/LayoutComponent.html" data-type="entity-link" >LayoutComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/LayoutSingleComponent.html" data-type="entity-link" >LayoutSingleComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/LoggedHomeComponent.html" data-type="entity-link" >LoggedHomeComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/LoginComponent.html" data-type="entity-link" >LoginComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PositionsComponent.html" data-type="entity-link" >PositionsComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PositionSingleComponent.html" data-type="entity-link" >PositionSingleComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ProjectComponent.html" data-type="entity-link" >ProjectComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SingleBenefitComponent.html" data-type="entity-link" >SingleBenefitComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/TableBuilderComponent.html" data-type="entity-link" >TableBuilderComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/UtentiComponent.html" data-type="entity-link" >UtentiComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ViewDepartmentComponent.html" data-type="entity-link" >ViewDepartmentComponent</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#directives-links"' :
                                'data-bs-target="#xs-directives-links"' }>
                                <span class="icon ion-md-code-working"></span>
                                <span>Directives</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="directives-links"' : 'id="xs-directives-links"' }>
                                <li class="link">
                                    <a href="directives/CrudBaseDirective.html" data-type="entity-link" >CrudBaseDirective</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#classes-links"' :
                            'data-bs-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Classes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/CustomForm.html" data-type="entity-link" >CustomForm</a>
                            </li>
                            <li class="link">
                                <a href="classes/DateQuestion.html" data-type="entity-link" >DateQuestion</a>
                            </li>
                            <li class="link">
                                <a href="classes/EmailQuestion.html" data-type="entity-link" >EmailQuestion</a>
                            </li>
                            <li class="link">
                                <a href="classes/JustInfo.html" data-type="entity-link" >JustInfo</a>
                            </li>
                            <li class="link">
                                <a href="classes/SelectForm.html" data-type="entity-link" >SelectForm</a>
                            </li>
                            <li class="link">
                                <a href="classes/SubForm.html" data-type="entity-link" >SubForm</a>
                            </li>
                            <li class="link">
                                <a href="classes/TextForm.html" data-type="entity-link" >TextForm</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#injectables-links"' :
                                'data-bs-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/CompanyDataService.html" data-type="entity-link" >CompanyDataService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/CrudFormService.html" data-type="entity-link" >CrudFormService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DataService.html" data-type="entity-link" >DataService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/DepartmentsService.html" data-type="entity-link" >DepartmentsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UserService.html" data-type="entity-link" >UserService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interfaces-links"' :
                            'data-bs-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/Benefit.html" data-type="entity-link" >Benefit</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Columns.html" data-type="entity-link" >Columns</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Contract.html" data-type="entity-link" >Contract</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Department.html" data-type="entity-link" >Department</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Employee.html" data-type="entity-link" >Employee</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Position.html" data-type="entity-link" >Position</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Project.html" data-type="entity-link" >Project</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/User.html" data-type="entity-link" >User</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/UserData.html" data-type="entity-link" >UserData</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/UserSession.html" data-type="entity-link" >UserSession</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#miscellaneous-links"'
                            : 'data-bs-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/enumerations.html" data-type="entity-link">Enums</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/typealiases.html" data-type="entity-link">Type aliases</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank" rel="noopener noreferrer">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});