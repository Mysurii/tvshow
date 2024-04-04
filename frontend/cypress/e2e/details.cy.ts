describe('details page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/shows/1');
  });

  it('should render TV show details when isLoading is false', () => {
    cy.get('[data-testid="loading-state"]').should('not.exist');
    cy.get('[data-testid="details"]').should('exist');
  });

  it('should redirect to 404 page if TV show is not available', () => {
    cy.visit('http://localhost:5173/shows/-12'); // Non-existent show ID

    cy.get('[data-testid="not found"]').should('exist');
  });
});
