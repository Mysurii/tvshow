describe('Home Page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/');
  });

  it('renders welcome message', () => {
    cy.contains('Welcome to TvShows').should('exist');
    cy.contains(
      'Discover your next favorite TV shows. Explore a wide range of genres and find top-rated shows recommended just for you.'
    ).should('exist');
  });

  it('redirects to correct URL when clicking on genre', () => {
    cy.contains('Crime').click();

    cy.url().should('include', '/shows?genre=crime');
  });

  it('should have atleast one tvshow in the list', () => {
    cy.get('[data-testid="tvshow-list"]')
      .should('exist')
      .find('li')
      .should('have.length.greaterThan', 0);
  });

  it('should render loading state when isLoading is true', () => {
    // Simulate isLoading being true
    cy.intercept('GET', '/api/tvShows', { delay: 1000 }).as('getTvShows');
    cy.get('[data-testid="loading-state"]').should('exist'); // Adjust .loading-spinner to your loading spinner class
  });

  it('displays genres section', () => {
    cy.get('[data-testid="display-genres"]').should('exist');
    cy.get('[data-testid="genre-1"]').should('exist');
  });

  it('displays recommended show list', () => {
    cy.contains('Recommended').should('exist');
  });

  it('displays latest TV shows list', () => {
    cy.contains('Latest TV Shows').should('exist');
  });

  it('displays recently updated list', () => {
    cy.contains('Recently updated').should('exist');
  });
});
