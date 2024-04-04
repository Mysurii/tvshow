describe('Tvshow page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/shows');
  });

  it('Should render all shows when tvShows data is available', () => {
    cy.get('[data-testid="tvshow-list"]')
      .should('exist')
      .find('li')
      .should('have.length.greaterThan', 0);
  });

  it('Should filter shows by genre when genre is provided', () => {
    cy.visit('http://localhost:5173/shows?genre=action');

    cy.get('[data-testid="tvshow-list"]')
      .should('exist')
      .find('li')
      .each((show) => {
        cy.wrap(show)
          .should('have.attr', 'data-genre')
          .then((genres) => {
            expect(genres).to.include('Action');
          });
      });
  });
});
