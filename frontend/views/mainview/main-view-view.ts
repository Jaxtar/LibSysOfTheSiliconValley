import { customElement, html, LitElement } from 'lit-element';

@customElement('main-view-view')
export class MainViewView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
